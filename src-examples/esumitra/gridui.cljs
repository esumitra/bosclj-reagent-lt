;;;; run lein figwheel esumitra and open a browser tab to
;;;; http://localhost:3449/index.html
(ns ^:figwheel-always esumitra.gridui
  (:require
   [clojure.string :as string]
   [reagent.core :as reagent]
   [garden.core :refer [css]]
   [garden.selectors :as s]
   [goog.style]
   [devcards.core :as dc :refer-macros [defcard deftest defcard-rg]]
   [esumitra.grid :as int-grid])
  (:require-macros [reagent.ratom :refer [reaction]]))

(defn pxl [x] (str x "px"))


;;; CSS for grid with Garden
(defn grid-css
  []
  [[:.new-grid-line:before {:content "'\\a'" :display "block"}]
   [:.grid-cell {:width (pxl 50)
                 :height (pxl 50)
                 :display "inline-block"
                 :border "thin solid black"
                 :transition "background-color .35s ease-in-out"}]
   [:.grid-cell-select {:background-color "rgba(0, 128, 255,1)"}]
   [:.grid-cell-unselect {:background-color "rgba(0, 128, 255,0.1)"}]
   [:.grid-label {:font-weight "bold"}]])

;;; Global application state
(def int-grid-size {:m 5 :n 5}) ;; 5 rows x 5 cols
(def grid-data (map #(reagent/atom 0)
                    (range (* (:m int-grid-size)
                              (:n int-grid-size)))))

(defn toggle-1
  "toggles between 0 and 1"
  [d] (get {0 1 1 0} d))

;;; Bounding box properties
(defn grid-selection-display
  "displays grid selection properties"
  [sel-props]
  [:div
   (for [[k v] sel-props]
     ^{:key k} [:div
                [:span.grid-label (str (name k) ": ")]
                [:span (str v)]])])

;;; Cell
(defn grid-span-cell
  "display grid item indicating selection using checkbox;
  toggle selection when clicked"
  [checked]
  (let [cell-class (str "grid-cell "
                        (if (= 1 @checked) "grid-cell-select" "grid-cell-unselect"))]
    [:span
     {:class cell-class
      :on-click (fn [e] (swap! checked toggle-1))
      :dangerouslySetInnerHTML {:__html "&nbsp;"}}]))

;;; Grid using global application state
(defn grid1
  "display grid with checkable items, calculate offset and dimensions for render
   using mobx style global application state"
  [data grid-item]
  (let [display-data (reaction (int-grid/grid-info
                                 (:m int-grid-size)
                                 (:n int-grid-size)
                                 (map deref data)))]
    [:div "Grid:"
     [:div
       (for [d (map-indexed #(vector %1 %2) data)
             :let [c (if (= 0 (mod (first d) (:n int-grid-size)))
                       "new-grid-line"
                       "")]]
         ^{:key (first d)} [:span {:class c} [grid-item (second d)]])]
     [:div "Selection properties:"
      [grid-selection-display @display-data]]]))

;;; Grid using local state
(defn grid2
  "display grid with checkable items, calculate offset and dimensions for render
   using local state"
  [m n grid-item]
  (let [local-grid-data (map #(reagent/atom 0)
                          (range (* m n)))
         display-data (reaction (int-grid/grid-info m n (map deref local-grid-data)))]
    (fn []
      [:div "Grid:"
       [:div
         (for [d (map-indexed #(vector %1 %2) local-grid-data)
               :let [c (if (= 0 (mod (first d) n))
                         "new-grid-line"
                         "")]]
           ^{:key (first d)} [:span {:class c} [grid-item (second d)]])]
       [:div "Selection properties:"
        [grid-selection-display @display-data]]])))

;;; ----------------
;;; Dev cards for UI
;;; ----------------
(defcard-rg selection-component
  "# Properties component
  Displays key-value pairs of input data"
  [grid-selection-display {:k1 "value1" :k2 "value2"}])

(defcard-rg cell-component
  "# Cell component
  Displays key-value pairs of input data"
  [grid-span-cell (reagent/atom 0)])

(defcard-rg grid-card-1
  "# Grid selection properties
  Allow the user to select cells in a grid and display selection properties in cell units

  * selection offsets from top left corner
  * selection width and height
  * using global application state"
  [grid1 grid-data grid-span-cell])

(defcard-rg grid-card-2
  "# Grid selection properties
  Allow the user to select cells in a grid and display selection properties in cell units

  * selection offsets from top left corner
  * selection width and height
  * using local state"
  [grid2 8 8 grid-span-cell])

;;; -------------------------
;;; bootstrap JS and CSS code
;;; -------------------------
(dc/start-devcard-ui!)

(defn all-css
  []
  ((juxt grid-css)))

(defn on-js-reload []
  (goog.style/installStyles (css (all-css))))
