package co.micode.omniclient.data

import co.micode.omniclient.api.services.OmniNewsService
import co.micode.omniclient.data.entities.ArticleEntity
import co.micode.omniclient.data.entities.TopicEntity
import javax.inject.Inject

class NewsDataSource
@Inject
constructor(private val service: OmniNewsService) {

    suspend fun fetchNews(query: String): Pair<List<ArticleEntity>, List<TopicEntity>> {
        val response = service.searchForTheNews(query)
        val articles = response.articles.map {
            ArticleEntity(
                it.title.value,
                it.main_resource.image_asset.id,
                it.main_text.paragraphs.map {
                    it.text.value
                }.joinToString("\n"))
        }
        val topics = response.topics.map {
            TopicEntity(
                it.title,
                it.type
            )
        }
        return articles to topics
    }
}