# GitHub Repositories API
This API allows you to fetch non-forked repositories of a specified GitHub user and their branch details.

# Table of Contents
- [Prerequisites](#prerequisites)
- [API Endpoints](#api-endpoints)
- [Query Parameters](#query-parameters)
- [Headers](#headers)
- [Example request](#example-request)
- [Response Format](#response-format)
- [Error Handling](#error-handling)
- [Technologies Used](#technologies-used)
- [Running the Application](#running-the-application)

# Prerequisites
- Java 21
- Maven
- Git
- Clone the Repository
- git clone https://github.com/biedrzyd/demo.git
- cd demo

# API Endpoints
#### Get GitHub Repositories  - Fetches all non-fork repositories for a specified username.
Method: GET\
Endpoint: /github-repositories

# Query Parameters

| Parameter | Type   | Description     |
|-----------|--------|-----------------|
| username  | string | Github username |

# Headers


| Header | Value            |
|--------|------------------|
| Accept | application/json |

# Example Request

curl -H "Accept: application/json" "http://localhost:8080/github-repositories?username=biedrzyd"

# Response Format
On success, the response will be a JSON array of repositories:

[
{
"repositoryName": "repository_name",
"ownerLogin": "owner_login",
"branches": [
{
"name": "branch_name",
"lastCommitSHA": "commit_sha"
}
]
}
]

# Error Handling
If the specified GitHub user does not exist, the API will return a 404 error with a structured response:

{
"status": 404,
"message": "User not found"
}

# Technologies Used
- Java 21
- Spring Boot 3.3.1
- Maven
- Jackson for JSON processing
- Apache HttpClient for external API calls 

# Running the Application
To run the application locally, use the following commands:

mvn clean install\
mvn spring-boot

The application will start on http://localhost:8080.