(ns day9
  (:require
   [clojure.math.combinatorics :as combo]))

(defn parse [apath]
  (map clojure.edn/read-string
       (clojure.string/split-lines (slurp apath))))

(def example (parse "./src/day9.example.txt"))
(def input (parse "./src/day9.input.txt"))

(defn preamble
  ([coll] (preamble coll 25))
  ([coll n] (take n coll)))

(defn combo-sums [coll]
  (map #(apply + %) (combo/combinations coll 2)))

(defn first-non-sum [coll preamble]
  (let [candidate (first coll)]
    (cond (empty? coll) :good-list
          (not-any? #{candidate} (combo-sums preamble)) [:bad-number candidate]
          :else (recur (rest coll) (concat (rest preamble) (list candidate))))))

;; part 1
(first-non-sum (drop 5 example) (take 5 example))
(first-non-sum (drop 25 input) (take 25 input))
