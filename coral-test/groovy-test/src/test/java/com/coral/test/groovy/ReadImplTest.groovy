import groovy.util.logging.Log

import java.util.logging.*

@Log
class EyGenReadImpl {
    EyGenReadImpl() {
        log.setLevel(Level.FINE)

    }


    def run(request) {
        log.info "request:" + request
    }

}


def read = new EyGenReadImpl()
read.run("test")