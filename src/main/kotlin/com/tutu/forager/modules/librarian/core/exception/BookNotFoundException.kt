package com.tutu.forager.modules.librarian.core.exception

class BookNotFoundException(id: String) : NotFoundException(
    message = "Não foi possivel encontrar livro com o id $id",
    code = "B-404",
    display = true,
    data = mapOf(
        "id" to id
    )
)