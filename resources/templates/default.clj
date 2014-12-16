;; POST TEMPLATE

(static.core/define-template base-template-file

  [:html] general-template

  [:.bg.dark-color :header] page-title

  [:.content.standard-layout :.main-area] 
    (enlive/html-content (-> content first :content))

  [:.content.standard-layout :.sidebar]
    (enlive/content (template-recent-post))

)
