(require '[clojure.java.shell :as shell])
(shell/sh "sh" "-c" "cd /etc; pwd")
{:exit 0, :out "/etc\n", :err ""}