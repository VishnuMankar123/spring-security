# -------------------------------------------------------------------------
# 1) Request password reset (forgot-password API)
#    - Method: POST
#    - Endpoint: /api/auth/forgot-password
#    - Input: email as form-data parameter
#    - Action: Sends password reset email with reset link
# -------------------------------------------------------------------------
curl --location 'http://localhost:8081/api/auth/forgot-password' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--header 'Cookie: JSESSIONID=7F7A5627AE764981BB5CCCEE5F07EA5E' \
--data-urlencode 'email=vishnudasmankar19@gmail.com'


# -------------------------------------------------------------------------
# 2) Validate reset token (validate-reset-token API)
#    - Method: GET
#    - Endpoint: /api/auth/validate-reset-token
#    - Input: token as query param
#    - Action: Validates whether the token is correct/active
# -------------------------------------------------------------------------
curl --location --request GET 'http://localhost:8081/api/auth/validate-reset-token?token=667686' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--header 'Cookie: JSESSIONID=7F7A5627AE764981BB5CCCEE5F07EA5E' \
--data-urlencode 'token=29767557-37ab-4355-af9a-764b4032b608'


# -------------------------------------------------------------------------
# 3) Reset password (reset-password API)
#    - Method: POST
#    - Endpoint: /api/auth/reset-password
#    - Input: JSON body (token + newPassword)
#    - Action: Resets the user password if token is valid
# -------------------------------------------------------------------------
curl --location 'http://localhost:8081/api/auth/reset-password' \
--header 'Content-Type: application/json' \
--header 'Cookie: JSESSIONID=7F7A5627AE764981BB5CCCEE5F07EA5E' \
--data-raw '{
"token": "667686",
"newPassword":"Vishnu@123"
}'
