LDGOLD ?= "${@bb.utils.contains('DISTRO_FEATURES', 'ld-is-gold', '--enable-gold=False --enable-threads', '--enable-gold --enable-ld=default --enable-threads', d)}"
