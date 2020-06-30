package com.zb.controller;

import com.google.gson.Gson;
import com.qiniu.http.Response;
import com.qiniu.storage.model.DefaultPutRet;
import com.zb.pojo.Material;
import com.zb.service.MaterialService;
import com.zb.service.UploadService;
import com.zb.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

/**
 * @author 王淑婷
 * @Description TODO
 * @Date 2020/6/13
 * @Version V1.0
 */
@RestController
@CrossOrigin
public class MaterialController {
    @Autowired
    private MaterialService materialService;

    @Autowired
    private UploadService uploadService;
    //根据年级id和科目id分页查询资料
    @GetMapping("/findMaterialPage")
    public PageUtil<Material> findMaterialPage(Integer index, Integer size, Integer gradeid, Integer subjectid){
       
        return materialService.findMaterialPage(index,size,subjectid,gradeid);
    }
    //唯一查询资料信息
    @GetMapping("/findMaterialById/{id}")
    public Material findMaterialById(@PathVariable("id") Long id){
        return materialService.findMaterialById(id);
    }

    @PostMapping("/singlefile")
    @Transactional
    public int singleFileUpload(@RequestBody Material material,HttpServletRequest request, @RequestParam(required = false,value = "files") MultipartFile[] files){
        for (MultipartFile file : files) {
            if (Objects.isNull(file) || file.isEmpty()) {
                //文件为空，请重新上传
                return -1;
            }
            try{
                //根据时间戳创建文件名
                String fileName = System.currentTimeMillis() + file.getOriginalFilename();
                //创建文件的实际路径
                String destFileName = request.getServletContext().getRealPath("") + "uploaded" + File.separator + fileName;
                //根据文件路径创建文件对应的实际文件
                File destFile = new File(destFileName);
                //创建文件实际路径
                destFile.getParentFile().mkdirs();
                //将文件传到对应的文件位置
                file.transferTo(destFile);


                Response response = uploadService.uploadFile(destFile);
                //解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                System.out.println(putRet.key);//这个就是从七牛云获取的文件名
                String fileUrl = "http://qbbxbtow0.bkt.clouddn.com/"+putRet.key;
                material.setFileUrl(fileUrl);
                int num = materialService.insertMaterialById(material);
                return num;
            }catch (IOException e){
                e.printStackTrace();
            }
//            announceService.save(announce);  //存入数据库

            /*try {
                byte[] bytes = file.getBytes();
                Path path = Paths.get("F:\\img\\" + file.getOriginalFilename());
                //如果没有files文件夹，则创建
                if (!Files.isWritable(path)) {
                    Files.createDirectories(Paths.get("F:\\img\\"));
                }
                //文件写入指定路径
                Files.write(path, bytes);
            } catch (IOException e) {
                e.printStackTrace();
                return "后端异常...";
            }*/
        }
        return 0;
    }
}
