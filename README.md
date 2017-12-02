# Search-Engine-for-Social-Networks
A search engine which retrieves the most relevant users according to their similarity with our query and their importance at the social network.

To measure the **similarity**  we convert our query and the tags of each user to the tf-idf space and then we compute the distance using a metric. In our case we can choose to use either the Euklideian distance or the Cosince similarity metrics.

To compute the **importance** of each user at the social network we use the HITS(Hyperlink-Induced Topic Search ) algorithm. The HITS algorithm for link analysis uses what it calls "Hubs" and "Authorities".  A good hub represents a user that follows many other users, and a good authority represents a user that is followed by many different hubs. We use the recursive property of the Authorities to compute an importance score for each user.




