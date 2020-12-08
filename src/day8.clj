(ns day8)

(def example (clojure.string/split-lines (slurp "./src/day8.example.txt")))
(def example-fixed (clojure.string/split-lines (slurp "./src/day8.example.fixed.txt")))
(def input (clojure.string/split-lines (slurp "./src/day8.input.txt")))

(defn boot
  ([coll] (boot coll 0 0 #{}))
  ([coll acc index visited-indexes]
   (let [x (get coll index)
         [_ op val-string] (and x (re-find #"([a-z]{3}) ([+|-][0-9]+)" x))
         val (clojure.edn/read-string val-string)
         updated-indexes (and index (conj visited-indexes index))]
     (cond
       (contains? visited-indexes index) [:infinite-loop acc]
       (= index (count coll)) [:done acc]
       (= op "nop") (recur coll acc (+ 1 index) updated-indexes)
       (= op "acc") (recur coll (+ acc val) (+ 1 index) updated-indexes)
       (= op "jmp") (recur coll acc (+ index val) updated-indexes)
       :else [:unknown-op updated-indexes]))))

;; part 1
(boot example)
(boot input)

;; part 2
(boot example-fixed)

(defn jump-indexes [coll]
  (keep-indexed #(when (re-find #"jmp" %2) %1) coll))

(defn boot-fix
  ([coll] (boot-fix coll (jump-indexes coll) nil))
  ([coll jumps last-replacement]
   (let [[status val] (boot coll)]
     (cond
       (= :done status) val
       (= :infinite-loop status) (let [new-replacement [(first jumps) (get coll (first jumps))]
                                       [last-index last-string] last-replacement
                                       restored-coll (if last-replacement
                                                       (assoc coll last-index last-string)
                                                       coll)]
                                   (recur (assoc restored-coll (first jumps) "nop +0")
                                          (rest jumps)
                                          [(first jumps) (get coll (first jumps))]))))))

(boot-fix example)
(boot-fix input)
