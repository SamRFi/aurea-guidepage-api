package apartments.aurea.guidepage_api.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.core.sync.RequestBody
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.GetObjectRequest
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import java.io.InputStream
import java.net.URL
import java.util.UUID

@Service
class S3Service(
    @Value("\${aws.accessKeyId}")
    private val awsAccessKeyId: String,
    @Value("\${aws.secretKey}")
    private val awsSecretKey: String,
    @Value("\${aws.s3.bucketName}")
    private val bucketName: String,
    @Value("\${aws.s3.region}")
    private val region: String
) {
    private val s3Client: S3Client = S3Client.builder()
        .region(Region.of(region))
        .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(awsAccessKeyId, awsSecretKey)))
        .build()

    fun uploadImage(imageStream: InputStream, contentType: String): String {
        val key = "images/${UUID.randomUUID()}"
        val putObjectRequest = PutObjectRequest.builder()
            .bucket(bucketName)
            .key(key)
            .contentType(contentType)
            .build()

        s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(imageStream, imageStream.available().toLong()))

        return key
    }

    fun getImageUrl(key: String): URL {
        return s3Client.utilities().getUrl { builder ->
            builder.bucket(bucketName).key(key)
        }
    }
}