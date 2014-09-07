user=> (re-find #"\d+" "abc123def")
"123"
user=> (re-matches #"\d+" "abc123def")
nil
user=> (re-matches #"\d+" "123")
"123"
