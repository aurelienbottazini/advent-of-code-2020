(ns day9
  (:require [clojure.math.combinatorics :as combo]))

(defn parse [apath]
  (map clojure.edn/read-string (clojure.string/split-lines (slurp apath))))

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

(first-non-sum (drop 5 example) (take 5 example))
(first-non-sum (drop 25 input) (take 25 input))

;; part 2
(defn encryption-weakness
  ([coll target-number] (encryption-weakness coll target-number '() 0))
  ([coll target-number contiguous-numbers contiguous-index]
   (let [contiguous-sum (apply + contiguous-numbers)]
     (cond (empty? coll) :no-weakness
           (= contiguous-sum target-number) (+ (apply min contiguous-numbers)
                                               (apply max contiguous-numbers))
           (> contiguous-sum target-number) (recur (rest coll) target-number (list (first coll)) 0)
           :else (recur coll target-number (conj contiguous-numbers (nth coll contiguous-index)) (inc contiguous-index))))))

(encryption-weakness example (nth (first-non-sum (drop 5 example) (take 5 example)) 1))
(encryption-weakness input (nth (first-non-sum (drop 25 input) (take 25 input)) 1))
