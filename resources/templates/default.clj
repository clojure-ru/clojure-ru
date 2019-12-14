;; POST TEMPLATE

(static.core/define-template base-template-file

  [:html] general-template

  [:.bg.dark-color :header] page-title

  [:.content.standard-layout :.main-area] 
    (enlive/html-content (-> content first :content))

  [:.content.standard-layout :.sidebar]
    (if-let [sidebar (-> metadata :sidebar)]
      (enlive/html-content (->> metadata
                                :projects
                                (filter #(-> % :sidebar empty? not))
                                (filter #(.startsWith (:sidebar %) sidebar))
                                (map (fn [x] [(:sidebar x) (:link x) {:title (:title x) :url (:link x)}]))
                                sort
                                (map last)
                                (map (fn [s]
                                       (if (= (:title metadata) (:title s))
                                         (str "<li>" (:title s) "</li>")
                                         (str "<li><a href=" (:url s) ">" (:title s) "</a></li>"))))
                                ((fn [links]
                                   (str "<div class=\"navigation\"><h4><strong>" sidebar "</strong></h4><ul class=\"menu\">" (apply str links) "</ul></div>")))))
      (enlive/content (template-recent-post)))
)
