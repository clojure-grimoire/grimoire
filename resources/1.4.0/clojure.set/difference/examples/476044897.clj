user=> (difference (set (keys {:1 1 :2 2 :3 3})) (set (keys {:1 1 :2 2})))
#{:3}
user=> (difference (set (keys {:1 1 :2 2})) (set (keys {:1 1 :2 2 :3 3})))
#{}