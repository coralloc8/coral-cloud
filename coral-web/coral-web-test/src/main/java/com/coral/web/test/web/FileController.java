package com.coral.web.test.web;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.coral.database.test.jpa.primary.enums.file.FileModuleEnum;
import com.coral.service.file.dto.FileSaveDTO;
import com.coral.service.file.service.IFileService;
import com.coral.service.file.vo.FileListResVO;
import com.coral.service.file.vo.FileResVO;
import com.coral.web.core.response.Result;
import com.coral.web.core.response.Results;
import com.coral.web.test.vo.BaseUploadVO;

/**
 * @author huss
 */
@RestController
@RequestMapping("/files")
public class FileController {

    @Autowired
    private IFileService fileService;

    @PostMapping("/upload")
    public Result upload(BaseUploadVO baseUploadVo) throws IOException {
        InputStream inputStream = baseUploadVo.getFile().getInputStream();
        String fileName = baseUploadVo.getFile().getOriginalFilename();
        FileSaveDTO fileSaveVo = new FileSaveDTO(inputStream, fileName, baseUploadVo.getRemark());
        FileResVO fileResVo = fileService.saveFile(fileSaveVo);
        return new Results().success(fileResVo);
    }

    @GetMapping("/{fileModule}/{infoNo}")
    public Result getOne(@PathVariable FileModuleEnum fileModule, @PathVariable String infoNo) {
        Optional<FileResVO> fileOpt = fileService.findByFileModuleAndInfoNo(fileModule, infoNo);
        if (fileOpt.isPresent()) {
            return new Results().success(fileOpt.get());
        }
        return new Results().success();

    }

    @GetMapping("/list/{fileModule}/{infoNo}")
    public Result getOne2(@PathVariable FileModuleEnum fileModule, @PathVariable String infoNo) {
        List<FileListResVO> listResVOS =
            fileService.findByFileModuleInAndInfoNoIn(Arrays.asList(fileModule), Arrays.asList(infoNo));
        return new Results().success(listResVOS);
    }

}
