(ns day1
  (:require [clojure.string :refer [split-lines]]
            [clojure.math.combinatorics :as combo]))

(def example '#{1721 979 366 299 675 1456})
(def expenses
  (into #{}
        (map #(Integer/parseInt %)
             (split-lines (slurp "./src/day1.input.txt")))))

;; part one
(apply * (filter #(contains? example (- 2020 %)) example))
(apply * (filter #(contains? expenses (- 2020 %)) expenses))

;; part two
(apply * (first (filter #(= 2020 (apply + %))
                        (combo/combinations example 3))))
(apply * (first (filter #(= 2020 (apply + %))
                        (combo/combinations expenses 3))))


