# FairShare API

A project for the course **Mobile Application Development**

This api can be accessed from 
```
https://ojasaar.com/fairshareapi
```

## Available Endpoints

### User Endpoints

#### Register a New User
```
POST /public/register
```

**Request Body:**
```json
{
  "email": "",
  "password": ""
}
```

#### User Login
```
POST /public/login
```

**Request Body:**
```json
{
  "email": "",
  "password": ""
}
```

---

### Group Endpoints

#### Create a New Group
```
POST /group
```

**Request Body:**
```json
{
  "name": ""
}
```

#### Get All Groups
```
GET /groups
```

#### Add Member to Group
```
POST /group/{groupId}/member
```

**Request Body:**
```json
{
  "userId": ""
}
```

**Path Parameters:**
- `groupId` - The ID of the group to add the member to