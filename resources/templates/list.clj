;; ARCHIVES TEMPLATE

(static.core/define-template site-template-file

  [:html] general-template

;; CONTENT BLOCK's

  [:.bg.dark-color :header :h1] (enlive/content 
                              (case (:type metadata)
                                :year-news (str (:title metadata) " Архивы")
                                :month-news (:title metadata)
                                (:title metadata)))

  [:.content.standard-layout :.main-area :.post] 
    (enlive/clone-for [{:keys [title url description date]} (mapcat second content)]
      [:h3 :a] (make-link title url)
      [:p.post-info] (enlive/content date)
      [:p :a] (enlive/set-attr :href url)
      [:div :div] (enlive/html-content description))

  [:.content.standard-layout :.sidebar]
    (when-not (#{:month-news :posts-for-tag} (:type metadata))
      (enlive/content (template-archive-post)))
)
