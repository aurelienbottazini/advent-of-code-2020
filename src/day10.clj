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
    (cond
      (nil? next-joltage) nil
      :else (conj (differences (rest coll)) (- next-joltage joltage)))))

(time
 (let [all-joltages (joltages input)
       jolt-diffs (differences all-joltages)]
   (* (count (filter #(= 1 %) jolt-diffs))
      (count (filter #(= 3 %) jolt-diffs)))))

;; part 2

(def distinct-arrangements
  (memoize
   (fn
     ([jolt-differences] 1)
     ([jolt-differences y & zs]
      (if (> (+ jolt-differences y) 3)
        (apply distinct-arrangements y zs)
        (+ (apply distinct-arrangements y zs)
           (apply distinct-arrangements (+ jolt-differences y) zs)))))))

(time
 (apply distinct-arrangements (differences (joltages input))))

