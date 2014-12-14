;; ARCHIVES TEMPLATE

(static.core/define-template site-template-file

;; CONTENT BLOCK's

  [:.bg.dark-color :header :h1] (enlive/content 
                              (case (:type metadata)
                                :year-news (str (:title metadata) " Архивы")
                                :month-news (str (:title metadata) " sdf")
                                (:title metadata)))

  [:.content.standard-layout :.main-area :.post] 
    (enlive/clone-for [{:keys [title url description date]} (mapcat second content)]
      [:h3 :a] (make-link title url)
      [:p.post-info] (enlive/content date)
      [:p :a] (enlive/set-attr :href url)
      [:div :div] (enlive/html-content description))

  [:.content.standard-layout :.sidebar]
    (when-not (= (:type metadata) :month-news)
      (enlive/content (template-archive-post)))
)
