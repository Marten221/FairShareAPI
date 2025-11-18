package com.ojasaar.fairshareapi.service;

import com.ojasaar.fairshareapi.domain.model.Expense;
import com.ojasaar.fairshareapi.domain.model.Group;
import com.ojasaar.fairshareapi.domain.model.User;
import com.ojasaar.fairshareapi.dto.DebtDTO;
import com.ojasaar.fairshareapi.dto.UserBalanceDTO;
import com.ojasaar.fairshareapi.repository.GroupRepo;
import com.ojasaar.fairshareapi.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class BalanceService {
    private final GroupRepo groupRepo;

    public List<DebtDTO> getGroupDebts(String groupId) {
        Group group = groupRepo.findGroupById(groupId);
        return netDebtsForGroup(group);
    }

    public List<UserBalanceDTO> getGroupUserBalances(String groupId) {
        Group group = groupRepo.findGroupById(groupId);
        return userBalances(group);
    }

    public UserBalanceDTO getUserBalance(String groupId) {
        Group group = groupRepo.findGroupById(groupId);
        String userId = UserUtil.getUserIdfromContext();

        return userBalances(group).stream()
                .filter(b -> b.userId().equals(userId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "User " + userId + " is not part of group " + groupId
                ));
    }

    public List<DebtDTO> getUserDebts(String groupId) {
        Group group = groupRepo.findGroupById(groupId);
        String userId = UserUtil.getUserIdfromContext();

        return netDebtsForGroup(group).stream()
                .filter(d -> d.fromUserId().equals(userId) || d.toUserId().equals(userId))
                .toList();
    }

    public Map<String, Map<String, Double>> buildRawDebts(Group group) {
        Map<String, Map<String, Double>> rawDebts = new HashMap<>();

        // For simplicity, assume all members participate in every expense
        Set<User> participants = group.getMembers();
        participants.add(group.getOwner());

        for (Expense e : group.getExpenses()) {
            User payer = e.getOwner();
            double amount = e.getAmount();

            int n = participants.size();

            double share = amount / n;

            for (User u : participants) {
                if (u.getId().equals(payer.getId())) {
                    // The payer doesn't owe themselves
                    continue;
                }

                rawDebts
                        .computeIfAbsent(u.getId(), k -> new HashMap<>())
                        .merge(payer.getId(), share, Double::sum);
            }
        }
        return rawDebts;
    }


    public List<DebtDTO> netDebtsForGroup(Group group) {
        Map<String, Map<String, Double>> rawDebts = buildRawDebts(group);

        // Collect all user ids involved
        Map<String, User> usersById = new HashMap<>();
        for (User u : group.getMembers()) {
            usersById.put(u.getId(), u);
        }

        List<String> userIds = usersById.keySet().stream().toList();
        List<DebtDTO> result = new ArrayList<>();

        for (int i = 0; i < userIds.size(); i++) {
            for (int j = i + 1; j < userIds.size(); j++) {
                String uId = userIds.get(i);
                String vId = userIds.get(j);

                double uOwesV = rawDebts.getOrDefault(uId, Map.of()).getOrDefault(vId, 0.0);
                double vOwesU = rawDebts.getOrDefault(vId, Map.of()).getOrDefault(uId, 0.0);

                double net = (double) Math.round((uOwesV - vOwesU) * 100) / 100;
                if (Math.abs(net) < 0.01) { // ignore tiny rounding noise
                    continue;
                }

                if (net > 0) {
                    // u owes v
                    User from = usersById.get(uId);
                    User to = usersById.get(vId);
                    result.add(new DebtDTO(
                            from.getId(), from.getEmail(),
                            to.getId(), to.getEmail(),
                            net
                    ));
                } else {
                    // v owes u
                    User from = usersById.get(vId);
                    User to = usersById.get(uId);
                    result.add(new DebtDTO(
                            from.getId(), from.getEmail(),
                            to.getId(), to.getEmail(),
                            -net
                    ));
                }
            }
        }

        return result;
    }


    public List<UserBalanceDTO> userBalances(Group group) {
        Map<String, Map<String, Double>> rawDebts = buildRawDebts(group);

        Map<String, User> usersById = new HashMap<>();
        for (User u : group.getMembers()) {
            usersById.put(u.getId(), u);
        }

        // total each user owes and is owed
        Map<String, Double> owes = new HashMap<>();
        Map<String, Double> isOwed = new HashMap<>();

        // rawDebts[from][to] = amount from owes to
        for (var fromEntry : rawDebts.entrySet()) {
            String fromId = fromEntry.getKey();
            for (var toEntry : fromEntry.getValue().entrySet()) {
                String toId = toEntry.getKey();
                double amount = toEntry.getValue();

                owes.merge(fromId, amount, Double::sum);
                isOwed.merge(toId, amount, Double::sum);
            }
        }

        List<UserBalanceDTO> balances = new ArrayList<>();
        for (var entry : usersById.entrySet()) {
            String userId = entry.getKey();
            User u = entry.getValue();

            double totalOwes = (double) Math.round(owes.getOrDefault(userId, 0.0) * 100) / 100;
            double totalIsOwed = (double) Math.round(isOwed.getOrDefault(userId, 0.0) * 100) / 100;
            double net = totalIsOwed - totalOwes;

            balances.add(new UserBalanceDTO(
                    userId,
                    u.getEmail(),
                    totalOwes,
                    totalIsOwed,
                    net
            ));
        }

        return balances;
    }


}
