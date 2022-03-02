const buttons= [...document.querySelectorAll("[buttons-grade]")]
const students_div=[...document.getElementsByName("name")]
const inputs=[...document.querySelectorAll("[inputs]")]
const divs=[...document.querySelectorAll("[all-button-divs]")]
const inputDivs=[...document.getElementsByClassName("hidden")]
const returnButton=[...document.getElementsByClassName("return-button")]
const subjects= [...document.querySelectorAll("[subject-name]")]
var students=new Array

students_div.forEach(s => students.push(s.textContent))

console.log(inputDivs)
var finalList=new Array

buttons.forEach((b,index) =>{
    b.addEventListener("click",e => {
        if(b.classList.contains("selected")){
            b.classList.remove("selected")
        }else {
            b.classList.add("selected")
            OpenTextBox(index)
        }
        
    })
})

returnButton.forEach((b,index)=>{
    b.addEventListener("click",e =>{
        CloseTextBox(index)
    })
})

inputs.forEach((i) =>{
    i.addEventListener("change",e=>{
        if(i.value>6){
            i.value=6
        }
        if(i.value<2){
            i.value=2
        }
    })
})

function OpenTextBox(inx) {
    let index=Math.floor(inx/5)
       
        setTimeout(function(){
            divs[index].classList.add("hidden")
        },300)

        inputs[index].value=buttons[inx].value

    setTimeout(function() {
        inputDivs[index].classList.remove("hidden")
    },600)

    setTimeout(function() {
        inputDivs[index].classList.remove("hide")
    },601)

        setTimeout(function(){
            divs[index].classList.add("hide")
        },602)


        
       
}

function CloseTextBox(index) {    
    setTimeout(function(){
        inputDivs[index].classList.add("hidden")
    },300)

    setTimeout(function() {
        divs[index].classList.remove("hidden")
    },600)

    setTimeout(function() {
        divs[index].classList.remove("hide")
    },601)

    setTimeout(function(){
        inputDivs[index].classList.add("hide")
    },602)



}

function getGrades() {
    let index=0
    let grade =0.0
   console.log(grade)
  inputs.forEach((b) => {
              grade = parseFloat(b.value)
      if(grade>0.0){
        finalList[index]=new Student(students[index],subjects[index].textContent,grade)
        index++
      }
        grade = 0.0
  })

    var json=JSON.stringify(finalList)
    console.log(json)
    var text = document.createElement('p')
    text.classList.add("hide","json")
    text.textContent=json
    console.log(text)
}



class Student {
    constructor(name,subject,grade){
        this.name=name
        this.subject=subject
        this.grade=grade
    }
}