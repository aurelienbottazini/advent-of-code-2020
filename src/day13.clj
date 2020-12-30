(ns day13)

(defn parse-input [path]
  (let [input (->> path slurp clojure.string/split-lines)]
    [(clojure.edn/read-string (first input))
     (->> input rest first
          (#(clojure.string/split % #","))
          (filter #(not= "x" %))
          (map #(clojure.edn/read-string %)))]))

(def example (parse-input "./src/day13.example"))
(def input (parse-input "./src/day13.input"))

(defn closest-bus-time
  ([time increment] (closest-bus-time time increment 0))
  ([time increment bus-time]
   (cond
     (>= bus-time time) [increment bus-time]
     :else (recur time increment (+ bus-time increment)))))

(let [time (first input)
      bus-ids (->> input rest first)
      bus-with-time (first
       (sort-by #(->> % rest first)
                (map #(closest-bus-time time %) bus-ids)))]
  (* (get bus-with-time 0)
     (- (get bus-with-time 1) time)))
