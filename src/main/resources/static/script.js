$(document).ready(function () {
    // edit halaman fasilitas
    $('.buton-edit').on('click', function(event) {
        event.preventDefault();

        var href = $(this).attr('href');

        $.get(href, function(fasilitasData) {
            // Use the fasilitasData object instead of 'fasilitas'
            $('#idEdit').val(fasilitasData.id);
            $('#namefasilitas').val(fasilitasData.nameFasilitas);
            $('#gambarBeforeEdit').val(fasilitasData.ImageFileName);
            $('#gambarEdit').attr('src', '/images/' + fasilitasData.imageFileName);
            console.log(fasilitasData.id);
            console.log(fasilitasData.nameFasilitas);
            console.log(fasilitasData.imageFileName);

        });

        $('#editModal').modal();
    });

    // save edit data
    $('#editForm').on('submit', function(event) {
        event.preventDefault();

        var formData = new FormData(this);

        $.ajax({
            url: $(this).attr('action'),
            type: 'POST',
            data: formData,
            processData: false,  // jQuery tidak memproses data
            contentType: false,  // jQuery tidak mengatur konten type
            success: function(response) {
                // Lakukan sesuatu setelah berhasil diupdate, seperti menutup modal dan refresh data
                $('#editModal').modal('hide');
                location.reload(); // Refresh halaman untuk melihat perubahan
            },
            error: function(jqXHR, textStatus, errorThrown) {
                for (const value of formData.values()) {
                    console.log(value);
                }
                console.log("Error: " + textStatus + " " + errorThrown);
                // Tampilkan pesan error atau lakukan sesuatu
            }
        });
    });

// edit halaman home
    $('.buton-edit-home').on('click', function(event) {
        event.preventDefault();

        var href = $(this).attr('href');

        $.get(href, function(fasilitasData) {
            // Use the fasilitasData object instead of 'fasilitas'
            $('#idEdit').val(fasilitasData.id);
            $('#gambarBeforeEdit').val(fasilitasData.ImageFileName);
            $('#gambarEdit').attr('src', '/images/' + fasilitasData.imageFileName);
            console.log(fasilitasData.id);
            console.log(fasilitasData.nameFasilitas);
            console.log(fasilitasData.imageFileName);

        });

        $('#editModal').modal();
    });

});
