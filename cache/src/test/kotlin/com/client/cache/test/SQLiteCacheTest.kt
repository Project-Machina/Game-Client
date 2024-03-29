package com.client.cache.test

import com.client.cache.Archive
import com.client.cache.compression.Compression
import com.client.cache.sql.SQLiteCache
import org.junit.jupiter.api.Test
import org.openjdk.nashorn.internal.ir.annotations.Ignore
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.io.path.exists

class SQLiteCacheTest {

    fun `test sqlite cache`() {
        val p = Paths.get("/home/david/IdeaProjects/GameClient/cache-test/")
        val pc = p.resolve("cache-0.pmcache")
        if (!pc.exists()) {
            Files.createFile(p.resolve("cache-0.pmcache"))
        }
        val cache = SQLiteCache(p)

        val index = cache.index(0)

        index.putArchive(Archive(0, 0L, "Hello, World".toByteArray()))

        val msg = String(cache.raw(0, 0))

        assert(msg.equals("hello, world", true)) { "Failed to pack and add archive, $msg" }

        val plainText = """
            The cat (Felis catus) is a domestic species of a small carnivorous mammal.[1][2] It is the only domesticated species in the family Felidae and is often referred to as the domestic cat to distinguish it from the wild members of the family.[4] A cat can either be a house cat, a farm cat or a feral cat; the latter ranges freely and avoids human contact.[5] Domestic cats are valued by humans for companionship and their ability to kill rodents. About 60 cat breeds are recognized by various cat registries.[6]

            The cat is similar in anatomy to the other felid species: it has a strong flexible body, quick reflexes, sharp teeth and retractable claws adapted to killing small prey. Its night vision and sense of smell are well developed. Cat communication includes vocalizations like meowing, purring, trilling, hissing, growling and grunting as well as cat-specific body language. A predator that is most active at dawn and dusk (crepuscular), the cat is a solitary hunter but a social species. It can hear sounds too faint or too high in frequency for human ears, such as those made by mice and other small mammals.[7] Cats also secrete and perceive pheromones.[8]

            Female domestic cats can have kittens from spring to late autumn, with litter sizes often ranging from two to five kittens.[9] Domestic cats are bred and shown at events as registered pedigreed cats, a hobby known as cat fancy. Population control of cats may be effected by spaying and neutering, but their proliferation and the abandonment of pets has resulted in large numbers of feral cats worldwide, contributing to the extinction of entire bird, mammal, and reptile species.[10]

            Cats were first domesticated in the Near East around 7500 BC.[11] It was long thought that cat domestication began in ancient Egypt, where cats were venerated from around 3100 BC.[12][13] As of 2021, there are an estimated 220 million owned and 480 million stray cats in the world.[14][15] As of 2017, the domestic cat was the second-most popular pet in the United States, with 95 million cats owned.[16][17][18] In the United Kingdom, 26% of adults have a cat with an estimated population of 10.9 million pet cats as of 2020.[19]
        """.trimIndent().toByteArray()

        cache.put(0, 1, plainText, Compression.GZIP)

        val rawData = cache.raw(0, 1)

        assert(plainText.size > rawData.size) { "Failed to gzip compress text." }

        val text = cache.data(0, 1)

        assert(text.contentEquals(plainText)) { "Failed to decompress text." }

    }

}