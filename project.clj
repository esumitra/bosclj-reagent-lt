(defproject bosclj_reagent_lt "0.0.1-SNAPSHOT"
  :description "Reagent Lightning Talk for Boston Clojure Group"
  :url "?"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.clojure/clojurescript "1.7.145"]
                 [reagent "0.5.1"]
                 [garden "1.3.0-SNAPSHOT"]]

  :source-paths ["src"]

  :clean-targets ^{:protect false} ["public/js/compiled"
                                    "resources-dev/public/js/compiled"
                                    :target-path]

  :profiles
  {:dev
   {:dependencies [[devcards "0.2.0-8"]]
    :plugins [[lein-cljsbuild "1.1.0"]
              [lein-figwheel "0.4.0"]]
    :resource-paths ["resources" "."]
    :cljsbuild
    {:builds
     {:esumitra
      {:source-paths ["src" "src-examples"]
       :figwheel {:on-jsload "esumitra.gridui/on-js-reload"
                  :devcards true}
       :compiler {:main esumitra.gridui
                  :asset-path "js/compiled/esumitra-out-dev"
                  :output-to "js/compiled/esumitra-dev.js"
                  :output-dir "js/compiled/esumitra-out-dev"
                  :optimizations :none
                  :source-map-timestamp true}}}}}}

  :figwheel {:css-dirs ["public/css"]
             :http-server-root "."
             :nrepl-port 7888})
