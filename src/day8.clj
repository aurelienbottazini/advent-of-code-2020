(ns day8)

(def example (clojure.string/split-lines (slurp "./src/day8.example.txt")))
(def input (clojure.string/split-lines (slurp "./src/day8.input.txt")))

(get example 2)

(defn boot
  ([input] (boot input 0 0 #{}))
  ([input acc index visited-indexes]
   (let [[_ op val-string] (re-find #"([a-z]{3}) ([+|-][0-9]+)" (get input index))
         val (clojure.edn/read-string val-string)
         updated-indexes (conj visited-indexes index)]
     (cond
       (contains? visited-indexes index) acc
       (= op "nop") (recur input acc (+ 1 index) updated-indexes)
       (= op "acc") (recur input (+ acc val) (+ 1 index) updated-indexes)
       (= op "jmp") (recur input acc (+ index val) updated-indexes)
       :else (println "unknown op: " updated-indexes)))))

(boot example)
(boot input)


