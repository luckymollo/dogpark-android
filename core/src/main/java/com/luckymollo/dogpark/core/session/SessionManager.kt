package com.luckymollo.dogpark.core.session

class SessionManager(private val sessionDao: SessionDao) {

    private lateinit var session: Session

    suspend fun getSession(): Session {
        return if (::session.isInitialized) {
            session
        } else {
            (sessionDao.getSession() ?: Session().also {
                sessionDao.insert(it)
            }).also {
                session = it
            }
        }
    }

    suspend fun clearSession() {
        session = Session().also {
            sessionDao.insert(it)
        }
    }
}
