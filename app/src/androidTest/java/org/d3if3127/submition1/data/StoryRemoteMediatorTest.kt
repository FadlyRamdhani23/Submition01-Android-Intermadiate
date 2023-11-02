package org.d3if3127.submition1.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingConfig
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import okhttp3.MultipartBody
import org.d3if3127.submition1.data.database.StoryDatabase
import org.d3if3127.submition1.data.response.FileUploadResponse
import org.d3if3127.submition1.data.response.ListStoryItem
import org.d3if3127.submition1.data.response.LoginResponse
import org.d3if3127.submition1.data.response.RegisterResponse
import org.d3if3127.submition1.data.response.StoryResponse
import org.d3if3127.submition1.data.retrofit.ApiService
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalPagingApi
@RunWith(AndroidJUnit4::class)
class StoryRemoteMediatorTest {

    private var mockApi: ApiService = FakeApiService()
    private var mockDb: StoryDatabase = Room.inMemoryDatabaseBuilder(
        ApplicationProvider.getApplicationContext(),
        StoryDatabase::class.java
    ).allowMainThreadQueries().build()

    @Test
    fun refreshLoadReturnsSuccessResultWhenMoreDataIsPresent() = runTest {
        val remoteMediator = StoryRemoteMediator(
            mockDb,
            mockApi,
        )
        val pagingState = PagingState<Int, ListStoryItem>(
            listOf(),
            null,
            PagingConfig(10),
            10
        )
        val result = remoteMediator.load(LoadType.REFRESH, pagingState)
        assertTrue(result is RemoteMediator.MediatorResult.Success)
        assertFalse((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
    }

    @After
    fun tearDown() {
        mockDb.clearAllTables()
    }
}

class FakeApiService : ApiService {

    override suspend fun getStories(page: Int, size: Int): StoryResponse {
        val items: MutableList<ListStoryItem> = arrayListOf()
        for (i in 0..100) {
            val quote = ListStoryItem(
                i.toString(),
                "author + $i",
                "quote $i",
            )
            items.add(quote)
        }
        return StoryResponse(items.subList((page - 1) * size, (page - 1) * size + size))
    }

    override suspend fun register(name: String, email: String, password: String): RegisterResponse {
        TODO("Not yet implemented")
    }

    override suspend fun login(email: String, password: String): LoginResponse {
        TODO("Not yet implemented")
    }
    override suspend fun uploadImage(
        file: MultipartBody.Part,
        description: String
    ): FileUploadResponse {
        TODO("Not yet implemented")
    }

    override suspend fun getStoriesWithLocation(location: Int): StoryResponse {
        TODO("Not yet implemented")
    }
}