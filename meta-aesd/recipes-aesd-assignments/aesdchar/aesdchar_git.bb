
# See https://git.yoctoproject.org/poky/tree/meta/files/common-licenses
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"


SRC_URI = "git://git@github.com/cu-ecen-aeld/assignments-3-and-later-MrSCAN.git;protocol=ssh;branch=master"

PV = "1.0+git${SRCPV}"
SRCREV = "eb196ad9c728d907d3848cf3fb25e2aa138a1c99"

# https://docs.yoctoproject.org/ref-manual/variables.html?highlight=workdir#term-WORKDIR

S = "${WORKDIR}/git/aesd-char-driver"

inherit module

EXTRA_OEMAKE:append:task-install = " -C ${STAGING_KERNEL_DIR} M=${S}"
EXTRA_OEMAKE += "KERNELDIR=${STAGING_KERNEL_DIR}"

# See https://git.yoctoproject.org/poky/plain/meta/conf/bitbake.conf?h=kirkstone
FILES:${PN} += "/etc /etc/init.d /etc/init.d/aesdchar-start-stop.sh /usr/bin/aesdchar_unload /usr/bin/aesdchar_load"


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


do_install:append () {
	install -d ${D}${bindir}
	install -m 0755 ${S}/aesdchar_load ${D}${bindir}/
	install -m 0755 ${S}/aesdchar_unload ${D}${bindir}/
    #install -m 0755 ${S}/aesdchar.ko ${D}${bindir}/

	install -d ${D}${sysconfdir}/init.d
	install -m 0755 ${S}/aesdchar-start-stop.sh ${D}${sysconfdir}/init.d/
}

inherit update-rc.d
INITSCRIPT_NAME = "aesdchar-start-stop.sh"
INITSCRIPT_PARAMS = "start 98 5 2 . stop 21 0 1 6 ."
