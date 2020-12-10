(ns day10)

(defn parse-file [filepath]
  (->> filepath slurp clojure.string/split-lines (map #(clojure.edn/read-string %)) sort))

(def example (parse-file "./src/day10.example.txt"))
(def example2 (parse-file "./src/day10.example2.txt"))
(def input (parse-file "./src/day10.input.txt"))

(defn built-in-joltage [coll]
  (+ 3 (last coll)))

(defn joltages [coll]
  `(0 ~@coll ~(built-in-joltage coll)))

(defn differences [coll]
  (let [joltage (first coll)
        next-joltage (first (rest coll))]
    (prn joltage next-joltage)
    (cond
      (nil? next-joltage) nil
      :else (conj  (differences (rest coll)) (- next-joltage joltage)))))

(let [all-joltages (joltages input)]
  (* (count (filter #(= 1 %) (differences all-joltages)))
     (count (filter #(= 3 %) (differences all-joltages)))))
