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

        $.get(href, function(homeData) {
            // Use the fasilitasData object instead of 'fasilitas'
            $('#idEdit').val(homeData.id);
            $('#gambarBeforeEdit').val(homeData.ImageFileName);
            $('#gambarEdit').attr('src', '/images/' + homeData.imageFileName);
            console.log(homeData.id);
            console.log(homeData.imageFileName);

        });
        $('#editModal').modal();
    });

    $('#editForm-home').on('submit', function(event) {
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
//     edit kamar
    $('.buton-edit-kamar').on('click', function(event) {
        event.preventDefault();

        var href = $(this).attr('href');

        $.get(href, function(kamarData) {
            // Use the fasilitasData object instead of 'fasilitas'
            $('#idEdit').val(kamarData.id);
            $('#namekamar').val(kamarData.namaKamar);
            $('#status option').each(function() {
                if ($(this).val() == kamarData.status) {
                    $(this).prop('selected', true);
                }
            });
            $('#gambarBeforeEdit').val(kamarData.ImageFileName);
            $('#gambarEdit').attr('src', '/images/' + kamarData.imageFileName);
            console.log(kamarData.id);
            console.log(kamarData.namaKamar);
            console.log(kamarData.status)
            console.log('Status dari database:', kamarData.status);
            console.log('Nilai dropdown setelah di-set:', $('#status').val());
            console.log(kamarData.imageFileName);

        });
        $('#editModal').modal();
    });
    $('#editForm-kamar').on('submit', function(event) {
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
});
