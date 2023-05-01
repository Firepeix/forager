package com.tutu.forager.modules.librarian.core.exception

class InvalidRequestException(vararg fields: String) : BaseException(
    message = "Campos invalidos encontrados",
    code = "B-001",
    display = true,
    data = mapOf(
        "fields" to fields
    )
)