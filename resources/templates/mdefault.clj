;; INDEX/POST TEMPLATE

(static.core/define-template base-template-file

;; STYLES

; remove sky's from background
  [:#header] (fn [header] (if (:type metadata)
                            ((enlive/set-attr :class "bg dark-color") header)
                            header))

;; CONTENT BLOCK's
; ON HOME
  [:.hero]      only-for-home

  [:#lang-info] only-for-home

  [:#news] (fn [news] (when (is-home?) 
                       ((enlive/do->
                         (enlive/content (map #(static.core/template-news-block-model %) 
                                              (take number-of-news-on-home content)))
                         (enlive/append (if (> (count content) number-of-news-on-home)
                                            (static.core/template-other-news-block-model 
                                              (drop number-of-news-on-home content))
                                            ""))) news)))

; ON POST
  [:.bg.dark-color :header] (when-not (is-home?) page-title)

  [:.content.standard-layout :.main-area] 
    (fn [post] (when-not (is-home?) 
      ((enlive/html-content (-> content first :content)) post)))

  [:.content.standard-layout :.sidebar]
    (fn [sidebar] (when (= :post (:type metadata)) 
                        ((enlive/content (template-recent-post)) sidebar)))

  ;;[:#dddeeebbb ] (enlive/content (static.core/latest-posts-sidebar))
)
