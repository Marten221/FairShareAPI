package com.ojasaar.fairshareapi.controller;

import com.ojasaar.fairshareapi.dto.DebtDTO;
import com.ojasaar.fairshareapi.dto.UserBalanceDTO;
import com.ojasaar.fairshareapi.dto.UserDebtsDTO;
import com.ojasaar.fairshareapi.service.BalanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BalanceController {
    private final BalanceService balanceService;

    @GetMapping("/debts/{groupId}")
    public ResponseEntity<List<DebtDTO>> getGroupDebts(@PathVariable String groupId) {
        return ResponseEntity.ok(balanceService.getGroupDebts(groupId));
    }

    @GetMapping("/balances/{groupId}")
    public ResponseEntity<List<UserBalanceDTO>> getGroupBalances(@PathVariable String groupId) {
        return ResponseEntity.ok(balanceService.getGroupUserBalances(groupId));
    }

    @GetMapping("/userbalance/{groupId}")
    public ResponseEntity<UserBalanceDTO> getUserBalance(@PathVariable String groupId) {
        return ResponseEntity.ok(balanceService.getUserBalance(groupId));
    }

    @GetMapping("/userdebts/{groupId}")
    public ResponseEntity<UserDebtsDTO> getUserDebt(@PathVariable String groupId) {
        return ResponseEntity.ok(balanceService.getUserDebts(groupId));
    }

}
