INSERT INTO reviews
    (user_id, show_id, review, stars, validated, created_at, updated_at)
VALUES
    (1, 1, 'Excellent spectacle, très belle prestation !', 5, TRUE,
        '2026-11-20 14:30:00', '2026-11-20 14:30:00'),

    (2, 1, 'Un spectacle très intéressant.', 4, TRUE,
        '2026-11-21 10:15:00', '2026-11-21 10:15:00'),

    (3, 2, NULL, 5, TRUE,
        '2026-11-22 16:45:00', '2026-11-22 16:45:00'),

    (1, 2, 'Je suis un peu déçu par ce spectacle.', 2, TRUE,
        '2026-11-23 18:20:00', '2026-11-23 18:20:00'),

    (2, 3, 'Une excellente découverte !', 5, FALSE,
        '2026-11-24 11:30:00', '2026-11-24 11:30:00'),

    (3, 3, NULL, 3, FALSE,
        '2026-11-25 15:00:00', '2026-11-25 15:00:00');
