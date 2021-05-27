package com.onfido.techtask.factlist

import android.annotation.SuppressLint
import com.onfido.techtask.OpenForTesting
import com.onfido.techtask.data.FactFavorite
import com.onfido.techtask.data.db.FactFavoritesDao
import com.onfido.techtask.network.di.NetworkScheduler
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.annotations.CheckReturnValue
import io.reactivex.disposables.Disposable
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

@OpenForTesting
internal class FactListRepository @Inject constructor(
    private val requests: FactListRequests,
    private val factFavoritesDao: FactFavoritesDao,
    @NetworkScheduler private val networkScheduler: Scheduler
) {

    @CheckReturnValue
    internal fun fetchFacts(): Single<List<FactListItem>> =
        requests
            .getCatFacts()
            .subscribeOn(networkScheduler)
            .map(Companion::mapToCatFactListItem)

    @CheckReturnValue
    fun observeFavorites(): Observable<List<FactFavorite>> =
        factFavoritesDao.getFavorites().subscribeOn(networkScheduler)

    @SuppressLint("RxJava2SubscribeMissingOnError")
    @CheckReturnValue
    fun favoriteRepo(factFavorite: FactFavorite): Disposable =
        factFavoritesDao.upsertFavorite(factFavorite).subscribe()

    @SuppressLint("RxJava2SubscribeMissingOnError")
    @CheckReturnValue
    fun unfavoriteRepo(factFavorite: FactFavorite): Disposable =
        factFavoritesDao.deleteFavorite(factFavorite).subscribe()

    companion object {
        private fun mapToCatFactListItem(response: List<FactModel>) =
            response.map {
                FactListItem(
                    text = it.text,
                    isVerified = it.status.verified,
                    isNew = it.isNew(),
                    createdAt = it.createdAt
                )
            }

        private fun FactModel.isNew() = try {
            val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
            val calendar = Calendar.getInstance()
            calendar.time = format.parse(createdAt)
            calendar.add(Calendar.DATE, 90)
            Calendar.getInstance().time.before(calendar.time)
        } catch (e: ParseException) {
            false
        }
    }
}
