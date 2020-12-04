(ns day7)

(def example (clojure.string/split-lines "light red bags contain 1 bright white bag, 2 muted yellow bags.
dark orange bags contain 3 bright white bags, 4 muted yellow bags.
bright white bags contain 1 shiny gold bag.
muted yellow bags contain 2 shiny gold bags, 9 faded blue bags.
shiny gold bags contain 1 dark olive bag, 2 vibrant plum bags.
dark olive bags contain 3 faded blue bags, 4 dotted black bags.
vibrant plum bags contain 5 faded blue bags, 6 dotted black bags.
faded blue bags contain no other bags.
dotted black bags contain no other bags."))

(def rules (clojure.string/split-lines (slurp "./src/day7.input.txt")))

(defn bag-lists [input]
  (reduce conj (map #(let [container-bag (first (rest (re-find #"(.*)\ bags contain " %)))]
                       {container-bag (reduce conj (map (fn [[x y]] (if-not (nil? x) {y (clojure.edn/read-string x)}))
                                                        (map (fn [x] (rest (re-find #"([0-9]+) (.*) bag" x)))
                                                             (clojure.string/split (first (rest (clojure.string/split % #"contain "))) #", "))))}) input)))

(defn direct-bags [input color]
  (filter identity (map #(if (get (nth % 1) color) (nth % 0)) (bag-lists input))))

(defn bags-for-color
  ([colors input] (bags-for-color colors input #{} #{}))
  ([colors input processed result]
   (cond
     (empty? colors) result
     (contains? processed (first colors)) (recur (drop 1 colors) input processed result)
     :else
     (recur (into (drop 1 colors) (direct-bags input (first colors)))
            input
            (conj processed (first colors))
            (into result (direct-bags input (first colors)))))))

(count
 (bags-for-color '("shiny gold") rules))

