
# See https://git.yoctoproject.org/poky/tree/meta/files/common-licenses
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"


SRC_URI = "git://git@github.com/cu-ecen-aeld/assignment-7-MrSCAN.git;protocol=ssh;branch=master"

PV = "1.0+git${SRCPV}"
SRCREV = "2440b2f7fb09cbcce8a494d655056c8557a4370b"

# https://docs.yoctoproject.org/ref-manual/variables.html?highlight=workdir#term-WORKDIR

S = "${WORKDIR}/git/misc-modules"

inherit module

EXTRA_OEMAKE:append:task-install = " -C ${STAGING_KERNEL_DIR} M=${S}"
EXTRA_OEMAKE += "KERNELDIR=${STAGING_KERNEL_DIR}"

# See https://git.yoctoproject.org/poky/plain/meta/conf/bitbake.conf?h=kirkstone
FILES:${PN} += "/etc /etc/init.d /etc/init.d/load_modules_start_stop.sh /usr/bin/module_load /usr/bin/module_unload"


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
	install -m 0755 ${S}/module_load ${D}${bindir}/
	install -m 0755 ${S}/module_unload ${D}${bindir}/

	install -d ${D}${sysconfdir}/init.d
	install -m 0755 ${S}/load_modules_start_stop.sh ${D}${sysconfdir}/init.d/
}

inherit update-rc.d
INITSCRIPT_NAME = "load_modules_start_stop.sh"
INITSCRIPT_PARAMS = "start 96 5 2 . stop 24 0 1 6 ."
