user> (def players (atom ()))
#'user/players

user> (swap! players conj :player1)
(:player1)

user> (swap! players conj :player2)
(:player2 :player1)

user> (deref players)
(:player2 :player1)