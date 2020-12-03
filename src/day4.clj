(ns day4
  (:require [clojure.string :refer [split-lines split includes?]]))

(def example
  (split "ecl:gry pid:860033327 eyr:2020 hcl:#fffffd
byr:1937 iyr:2017 cid:147 hgt:183cm

iyr:2013 ecl:amb cid:350 eyr:2023 pid:028048884
hcl:#cfa07d byr:1929

hcl:#ae17e1 iyr:2013
eyr:2024
ecl:brn pid:760753108 byr:1931
hgt:179cm

hcl:#cfa07d eyr:2025 pid:166559648
iyr:2011 ecl:brn hgt:59in" #"\n\n"))

(def passport-entries (split (slurp "./src/day4.input.txt") #"\n\n"))

(def mandatory-fields '("byr" "iyr" "eyr" "hgt" "hcl" "ecl" "pid"))

(defn check-passport-entry [entry]
  (every? true? (map #(includes? entry (str % ":")) mandatory-fields)))

(count (filter true? (map check-passport-entry example)))
(count (filter true? (map check-passport-entry passport-entries)))



