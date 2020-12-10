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
      :else (conj  (differences (rest coll)) (- next-joltage joltage)))))

(let [all-joltages (joltages input)]
  (* (count (filter #(= 1 %) (differences all-joltages)))
     (count (filter #(= 3 %) (differences all-joltages)))))

(joltages example)
(prn
 (differences (joltages example))
 )

(defn max-3 [coll]
  )

  (= '(3 3) '(1 3))

(max-3 '(1 3))
(max-3 '(1 1 1))

(max-3 '(3 1 1))
;; (1 3 1 1 1 3 1 1 3 1 3 3)

;; (1 3) => 1
;; (1 1 1) => 3
;; (3 1 1) => 2
;; (3 1) => 1
;; (1 3) => 1
;; (3 3) => 1

(1,1,1) => 3
(3,1,1) => 2

(1 3 1) => 1
(3,1,3) => 1
(1,3,1) => 1
(3,3,1) => 1

;; ()        (1)
;; (1,3)
;;            (1,3,1)
;;                             (1,1)
;; (0), 1,    4, 5,            6, 7, 10, 11, 12, 15, 16, 19, (22)
;; (0), 1,    4, 5,            6, 7, 10, 12, 15, 16, 19, (22)
;;                             (1,3)
;; (0), 1,    4, 5,            7, 10, 11, 12, 15, 16, 19, (22)
;; (0), 1,    4, 5,            7, 10, 12, 15, 16, 19, (22)

;;            (1,3,1,1)
;; (0), 1,    4, 6,            7, 10, 11, 12, 15, 16, 19, (22)
;; (0), 1,    4, 6,            7, 10, 12, 15, 16, 19, (22)

;;            (1,3,1,1,1)
;; (0), 1,    4, 7,            10, 11, 12, 15, 16, 19, (22)
;; (0), 1,    4, 7,            10, 12, 15, 16, 19, (22)

