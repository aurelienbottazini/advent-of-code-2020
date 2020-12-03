(ns day4
  (:require [clojure.string :refer [split-lines split includes?]]
            [clojure.edn :refer [read-string]]))

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

(def invalid-passports
  (split "eyr:1972 cid:100
hcl:#18171d ecl:amb hgt:170 pid:186cm iyr:2018 byr:1926

iyr:2019
hcl:#602927 eyr:1967 hgt:170cm
ecl:grn pid:012533040 byr:1946

hcl:dab227 iyr:2012
ecl:brn hgt:182cm pid:021572410 eyr:2020 byr:1992 cid:277

hgt:59cm ecl:zzz
eyr:2038 hcl:74454a iyr:2023
pid:3556412378 byr:2007" #"\n\n"))

(def valid-passports
  (split "pid:087499704 hgt:74in ecl:grn iyr:2012 eyr:2030 byr:1980
hcl:#623a2f

eyr:2029 ecl:blu cid:129 byr:1989
iyr:2014 pid:896056539 hcl:#a97842 hgt:165cm

hcl:#888785
hgt:164cm byr:2001 iyr:2015 cid:88
pid:545766238 ecl:hzl
eyr:2022

iyr:2010 hgt:158cm hcl:#b6652a ecl:blu byr:1944 eyr:2021 pid:093154719 " #"\n\n"))


(def passport-entries (split (slurp "./src/day4.input.txt") #"\n\n"))

(def mandatory-fields '("byr" "iyr" "eyr" "hgt" "hcl" "ecl" "pid"))

(def mandatory-checks
  [#(let [byr (read-string (get (re-find #"byr:([0-9]{4})\b" %1) 1))]
           (and ((comp not nil?) byr)(>= byr 1920) (<= byr 2002)))
   #(let [iyr (read-string (get (re-find #"iyr:([0-9]{4})\b" %1) 1))]
           (and ((comp not nil?) iyr)(>= iyr 2010) (<= iyr 2020)))
   #(let [eyr (read-string (get (re-find #"eyr:([0-9]{4})\b" %1) 1))]
           (and ((comp not nil?) eyr)(>= eyr 2020) (<= eyr 2030)))
   #(let [matches (re-find #"hgt:([0-9]+)(cm|in)\b" %1)
               height (read-string (get matches 1))
               unit (get matches 2)]
           (and ((comp not nil?) matches)
                (if (= "cm" unit)
                  (and (>= height 150) (<= height 193))
                  (and (>= height 59) (<= height 76)))))
   #(let [hcl (re-find #"hcl:#[0-9|a-f]{6}\b" %1)]
           ((comp not nil?) hcl))
   #(let [ecl (re-find #"ecl:amb|blu|brn|gry|grn|hzl|oth\b" %1)]
           ((comp not nil?) ecl))
   #(let [pid (re-find #"pid:[0-9]{9}\b" %1)]
           ((comp not nil?) pid))])

(defn check-passport-entry [entry]
  (and
   (every? true? (map #(includes? entry (str % ":")) mandatory-fields))
   (every? true? (map #(%1 entry) mandatory-checks))))

(count (filter true? (map check-passport-entry example)))
(count (filter true? (map check-passport-entry invalid-passports)))
(count (filter true? (map check-passport-entry valid-passports)))

(count (filter true? (map check-passport-entry passport-entries)))



