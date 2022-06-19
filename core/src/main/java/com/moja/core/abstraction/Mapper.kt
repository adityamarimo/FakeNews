package com.moja.core.abstraction

interface Mapper<in Raw, out Domain> {
    fun map(raw: Raw) : Domain
}