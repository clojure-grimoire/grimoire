user=> (import java.util.Date)
java.util.Date

user=> (def *now* (Date.))
#'user/*now*

user=> (bean *now*)
{:seconds 57, :date 13, :class java.util.Date, :minutes 55, :hours 17, :year 110, :timezoneOffset -330, :month 6, :day 2, :time 1279023957492}
