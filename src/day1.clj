(ns day1
  (:require [clojure.string :refer [split-lines]]))

(def expenses
  (into #{}
        (map #(Integer/parseInt %)
             (split-lines (slurp "./src/day1.input.txt")))))

(apply * (filter #(contains? expenses (- 2020 %)) expenses))
