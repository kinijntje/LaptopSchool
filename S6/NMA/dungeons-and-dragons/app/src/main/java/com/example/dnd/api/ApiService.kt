import com.example.dnd.api.PostData
import com.example.dnd.api.ResponseData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("register")
    suspend fun registerUser(@Body postData: PostData): Response<ResponseData>

    @POST("login")
    suspend fun loginUser(@Body postData: PostData): Response<ResponseData>
}