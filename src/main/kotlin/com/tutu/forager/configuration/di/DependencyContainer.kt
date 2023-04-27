package com.tutu.forager.configuration.di

import com.tutu.forager.modules.librarian.configuration.LibrarianContainer
import com.tutu.forager.modules.librarian.configuration.create
import me.tatarka.inject.annotations.Component

@Component
abstract class DependencyContainer {
    val librarianContainer: LibrarianContainer = LibrarianContainer::class.create()
}