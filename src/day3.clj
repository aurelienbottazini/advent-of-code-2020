(ns day3
  (:require [clojure.string :refer [split-lines]]))

(def example (split-lines "..##.......
#...#...#..
.#....#..#.
..#.#...#.#
.#...##..#.
..#.##.....
.#.#.#....#
.#........#
#.##...#...
#...##....#
.#..#...#.#"))

(def map (split-lines
 (slurp "./src/day3.input.txt")))

(def moves {:right 3 :down 1})

(defn slide-toboggan [map number-of-trees number-of-steps]
  (cond
    (empty? map) number-of-trees
    :else (if (= \# (square (first map) (:right moves) number-of-steps))
            (recur (move-map (:down moves) map) (+ 1 number-of-trees) (+ 1 number-of-steps))
            (recur (move-map (:down moves) map) number-of-trees (+ 1 number-of-steps)))))

(defn move-map [move-down map]
  (drop move-down map))

(defn square [line move-right number-of-steps]
  (if (<= (count line) (* move-right number-of-steps))
    (recur (str line line) move-right number-of-steps)
    (get line (* move-right number-of-steps))))

(slide-toboggan example 0 0)
(slide-toboggan map 0 0)
