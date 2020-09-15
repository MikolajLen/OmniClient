package co.micode.omniclient.data

import co.micode.omniclient.api.model.generated.OmniNewsResponse
import co.micode.omniclient.api.services.OmniNewsService
import co.micode.omniclient.data.entities.ArticleEntity
import co.micode.omniclient.data.entities.TopicEntity
import com.google.common.truth.Truth.assertThat
import com.google.gson.Gson
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

class NewsDataSourceTest {

    val mockedService = mock<OmniNewsService> { }

    @ExperimentalCoroutinesApi
    @Test
    fun `should properly parse data`() = runBlockingTest {
        //given
        whenever(mockedService.searchForTheNews(any())).thenReturn(
            Gson().fromJson(
                TEST_JSON,
                OmniNewsResponse::class.java
            )
        )
        val underTest = NewsDataSource(mockedService)

        //when
        val (articles, topics) = underTest.fetchNews("test")

        //then
        verifyArticles(articles)
        verifyTopics(topics)
    }

    private fun verifyTopics(topics: List<TopicEntity>) {
        assertThat(topics.size).isEqualTo(1)
        assertThat(topics[0].title).isEqualTo("Test 1234")
        assertThat(topics[0].type).isEqualTo("descriptor")
    }

    private fun verifyArticles(articles: List<ArticleEntity>) {
        assertThat(articles.size).isEqualTo(1)
        assertThat(articles[0].imageId).isEqualTo("6b94c55e-119a-4a2e-8b19-cdc4f26f472e")
        assertThat(articles[0].text).isEqualTo(
            "Den kinesiska allmänheten kan få tillgång till ett vaccin mot covid-19 redan i november eller december. Det säger Wu Guizhen, talesperson vid Kinas nationella smittskyddsmyndighet, rapporterar Reuters.\n" +
                    "I en intervju med statlig tv säger Wu Guizhen att fas 3 i testningen av vaccinet går enligt plan och att hon själv testat ett experimentellt vaccin redan i april.\n" +
                    "Kina uppger sig ha fyra olika vaccin som är i sista testningsfasen."
        )
        assertThat(articles[0].title).isEqualTo("Kina kan börja dela ut vaccin redan i november")
    }
}

const val TEST_JSON = "{\n" +
        "\t\"articles\": [{\n" +
        "\t\t\"article_id\": \"kRaMVv\",\n" +
        "\t\t\"title\": {\n" +
        "\t\t\t\"value\": \"Kina kan börja dela ut vaccin redan i november\"\n" +
        "\t\t},\n" +
        "\t\t\"authors\": [{\n" +
        "\t\t\t\"id\": \"ca358124-7779-4e9f-ac60-ffe63df8f30d\",\n" +
        "\t\t\t\"image\": \"8e5ae4a7-b09b-4b81-9f49-e421dcd7ccc1\",\n" +
        "\t\t\t\"title\": \"Josefin Pehrson\"\n" +
        "\t\t}],\n" +
        "\t\t\"resources\": [{\n" +
        "\t\t\t\"type\": \"Image\",\n" +
        "\t\t\t\"byline\": {\n" +
        "\t\t\t\t\"title\": \"Andy Wong / TT NYHETSBYRÅN\"\n" +
        "\t\t\t},\n" +
        "\t\t\t\"caption\": {\n" +
        "\t\t\t\t\"value\": \"Illustrationsbild, annan vaccintillverkning i Kina.\"\n" +
        "\t\t\t},\n" +
        "\t\t\t\"source_id\": \"sdlqkUrX5obnxQ-nh.jpg\",\n" +
        "\t\t\t\"image_asset\": {\n" +
        "\t\t\t\t\"id\": \"6b94c55e-119a-4a2e-8b19-cdc4f26f472e\",\n" +
        "\t\t\t\t\"size\": {\n" +
        "\t\t\t\t\t\"width\": 3000,\n" +
        "\t\t\t\t\t\"height\": 2203\n" +
        "\t\t\t\t},\n" +
        "\t\t\t\t\"hotspot\": {\n" +
        "\t\t\t\t\t\"x\": 248,\n" +
        "\t\t\t\t\t\"y\": 507,\n" +
        "\t\t\t\t\t\"width\": 1759,\n" +
        "\t\t\t\t\t\"height\": 1318\n" +
        "\t\t\t\t}\n" +
        "\t\t\t}\n" +
        "\t\t}],\n" +
        "\t\t\"meta\": {\n" +
        "\t\t\t\"changes\": {\n" +
        "\t\t\t\t\"bumped\": null,\n" +
        "\t\t\t\t\"created\": \"2020-09-15T05:49:48Z\",\n" +
        "\t\t\t\t\"updated\": \"2020-09-15T06:15:38Z\",\n" +
        "\t\t\t\t\"published\": \"2020-09-15T06:04:52Z\",\n" +
        "\t\t\t\t\"bumped_value\": false,\n" +
        "\t\t\t\t\"first_published\": \"2020-09-15T06:04:52Z\"\n" +
        "\t\t\t},\n" +
        "\t\t\t\"version\": 10,\n" +
        "\t\t\t\"is_sticky\": false,\n" +
        "\t\t\t\"newsvalue\": 9,\n" +
        "\t\t\t\"is_deleted\": false,\n" +
        "\t\t\t\"is_sponsored\": false,\n" +
        "\t\t\t\"machine_tags\": {\n" +
        "\t\t\t\t\"gender\": \"male\"\n" +
        "\t\t\t},\n" +
        "\t\t\t\"news_lifetime\": 2,\n" +
        "\t\t\t\"custom_properties\": {\n" +
        "\t\t\t\t\"bump\": false,\n" +
        "\t\t\t\t\"sticky\": \"\",\n" +
        "\t\t\t\t\"longread\": false\n" +
        "\t\t\t},\n" +
        "\t\t\t\"presentation_properties\": {}\n" +
        "\t\t},\n" +
        "\t\t\"tags\": [{\n" +
        "\t\t\t\"type\": \"location\",\n" +
        "\t\t\t\"title\": \"Kina\",\n" +
        "\t\t\t\"topic_id\": \"350bd4e62c0927bdcd0419853c6b238d8875e50d\"\n" +
        "\t\t}],\n" +
        "\t\t\"type\": \"Article\",\n" +
        "\t\t\"category\": {\n" +
        "\t\t\t\"category_id\": \"feddd38a2f914a06a6f851eeb32a7eb7\",\n" +
        "\t\t\t\"title\": \"Utrikes\",\n" +
        "\t\t\t\"slug\": \"utrikes\",\n" +
        "\t\t\t\"large_threshold\": 7,\n" +
        "\t\t\t\"icon\": {\n" +
        "\t\t\t\t\"image_url\": \"https://storage.omni.se/category_icons/omni/icUtrikes.png\",\n" +
        "\t\t\t\t\"color\": {\n" +
        "\t\t\t\t\t\"red\": 34,\n" +
        "\t\t\t\t\t\"green\": 34,\n" +
        "\t\t\t\t\t\"blue\": 34\n" +
        "\t\t\t\t}\n" +
        "\t\t\t},\n" +
        "\t\t\t\"color\": {\n" +
        "\t\t\t\t\"red\": 92,\n" +
        "\t\t\t\t\"green\": 142,\n" +
        "\t\t\t\t\"blue\": 129\n" +
        "\t\t\t},\n" +
        "\t\t\t\"default\": 0.66\n" +
        "\t\t},\n" +
        "\t\t\"changes\": {\n" +
        "\t\t\t\"published\": \"2020-09-15T06:04:52Z\",\n" +
        "\t\t\t\"modified\": \"2020-09-15T06:15:38Z\"\n" +
        "\t\t},\n" +
        "\t\t\"story\": {\n" +
        "\t\t\t\"type\": \"story\",\n" +
        "\t\t\t\"title\": \"Jakten på botemedel\",\n" +
        "\t\t\t\"topic_id\": \"7fccac9e-896b-4379-87db-aebb92f8d9a8\"\n" +
        "\t\t},\n" +
        "\t\t\"main_text\": {\n" +
        "\t\t\t\"type\": \"Text\",\n" +
        "\t\t\t\"paragraphs\": [{\n" +
        "\t\t\t\t\"text\": {\n" +
        "\t\t\t\t\t\"value\": \"Den kinesiska allmänheten kan få tillgång till ett vaccin mot covid-19 redan i november eller december. Det säger Wu Guizhen, talesperson vid Kinas nationella smittskyddsmyndighet, rapporterar Reuters.\"\n" +
        "\t\t\t\t},\n" +
        "\t\t\t\t\"block_type\": \"paragraph\"\n" +
        "\t\t\t}, {\n" +
        "\t\t\t\t\"text\": {\n" +
        "\t\t\t\t\t\"value\": \"I en intervju med statlig tv säger Wu Guizhen att fas 3 i testningen av vaccinet går enligt plan och att hon själv testat ett experimentellt vaccin redan i april.\"\n" +
        "\t\t\t\t},\n" +
        "\t\t\t\t\"block_type\": \"paragraph\"\n" +
        "\t\t\t}, {\n" +
        "\t\t\t\t\"text\": {\n" +
        "\t\t\t\t\t\"value\": \"Kina uppger sig ha fyra olika vaccin som är i sista testningsfasen.\"\n" +
        "\t\t\t\t},\n" +
        "\t\t\t\t\"block_type\": \"paragraph\"\n" +
        "\t\t\t}],\n" +
        "\t\t\t\"vignette\": {\n" +
        "\t\t\t\t\"title\": \"Jakten på botemedel\",\n" +
        "\t\t\t\t\"bullet_color\": {\n" +
        "\t\t\t\t\t\"red\": 92,\n" +
        "\t\t\t\t\t\"green\": 142,\n" +
        "\t\t\t\t\t\"blue\": 129\n" +
        "\t\t\t\t},\n" +
        "\t\t\t\t\"supertag\": {\n" +
        "\t\t\t\t\t\"topic_id\": \"3ee2d7f6-56f1-4573-82b9-a4164cbdc902\",\n" +
        "\t\t\t\t\t\"title\": \"Coronaviruset\",\n" +
        "\t\t\t\t\t\"text\": \"Allt om virusets effekter och spridning\"\n" +
        "\t\t\t\t}\n" +
        "\t\t\t}\n" +
        "\t\t},\n" +
        "\t\t\"main_resource\": {\n" +
        "\t\t\t\"type\": \"Image\",\n" +
        "\t\t\t\"byline\": {\n" +
        "\t\t\t\t\"title\": \"Andy Wong / TT NYHETSBYRÅN\"\n" +
        "\t\t\t},\n" +
        "\t\t\t\"caption\": {\n" +
        "\t\t\t\t\"value\": \"Illustrationsbild, annan vaccintillverkning i Kina.\"\n" +
        "\t\t\t},\n" +
        "\t\t\t\"source_id\": \"sdlqkUrX5obnxQ-nh.jpg\",\n" +
        "\t\t\t\"image_asset\": {\n" +
        "\t\t\t\t\"id\": \"6b94c55e-119a-4a2e-8b19-cdc4f26f472e\",\n" +
        "\t\t\t\t\"size\": {\n" +
        "\t\t\t\t\t\"width\": 3000,\n" +
        "\t\t\t\t\t\"height\": 2203\n" +
        "\t\t\t\t},\n" +
        "\t\t\t\t\"hotspot\": {\n" +
        "\t\t\t\t\t\"x\": 248,\n" +
        "\t\t\t\t\t\"y\": 507,\n" +
        "\t\t\t\t\t\"width\": 1759,\n" +
        "\t\t\t\t\t\"height\": 1318\n" +
        "\t\t\t\t}\n" +
        "\t\t\t}\n" +
        "\t\t},\n" +
        "\t\t\"teaser_layout\": \"Small\",\n" +
        "\t\t\"story_vignette\": {\n" +
        "\t\t\t\"ref\": \"topic\",\n" +
        "\t\t\t\"id\": \"7fccac9e-896b-4379-87db-aebb92f8d9a8\",\n" +
        "\t\t\t\"title\": \"Jakten på botemedel\",\n" +
        "\t\t\t\"supertag\": {\n" +
        "\t\t\t\t\"topic_id\": \"3ee2d7f6-56f1-4573-82b9-a4164cbdc902\",\n" +
        "\t\t\t\t\"title\": \"Coronaviruset\",\n" +
        "\t\t\t\t\"text\": \"Allt om virusets effekter och spridning\"\n" +
        "\t\t\t}\n" +
        "\t\t}\n" +
        "\t}],\n" +
        "\t\"topics\": [{\n" +
        "\t\t\"topic_id\": \"a5b87892-c0fa-46e3-a7f4-016b77943fa0\",\n" +
        "\t\t\"title\": \"Test 1234\",\n" +
        "\t\t\"type\": \"descriptor\",\n" +
        "\t\t\"image\": null\n" +
        "\t}]\n" +
        "}"