user=> (let [m {:1 1 :2 2 :3 3 :4 4}] (map list (keys m) (vals m)))
((:1 1) (:2 2) (:3 3) (:4 4))