
# See https://git.yoctoproject.org/poky/tree/meta/files/common-licenses
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"


SRC_URI = "git://git@github.com/cu-ecen-aeld/assignments-3-and-later-MrSCAN.git;protocol=ssh;branch=master"

PV = "1.0+git${SRCPV}"
SRCREV = "58d480f146e5fd3cce8c949a6611a6b1b0289f2c"

# https://docs.yoctoproject.org/ref-manual/variables.html?highlight=workdir#term-WORKDIR

S = "${WORKDIR}/git/server"

# See https://git.yoctoproject.org/poky/plain/meta/conf/bitbake.conf?h=kirkstone
FILES:${PN} += "${bindir}/aesdsocket"
FILES:${PN} += "${sysconfdir}/init.d/aesdsocket-start-stop.sh"

TARGET_LDFLAGS += "-pthread -lrt"

RDEPENDS:${PN} += "libgcc"



do_configure () {
	:
}

do_deploy () {
	:
}

do_compile () {
	oe_runmake
}

inherit update-rc.d
INITSCRIPT_NAME = "aesdsocket-start-stop.sh"
INITSCRIPT_PARAMS = "start 99 5 2 . stop 20 0 1 6 ."


do_install () {
	install -d ${D}${bindir}
    install -m 0755 ${S}/aesdsocket ${D}${bindir}/

	install -d ${D}${sysconfdir}/init.d
    install -m 0755 ${S}/aesdsocket-start-stop.sh ${D}${sysconfdir}/init.d/
}
